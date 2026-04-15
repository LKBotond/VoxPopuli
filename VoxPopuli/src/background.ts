import type { RuntimeMessage } from "./types/MessageTypes";
import type { SessionToken } from "./contracts/Auth";
import { loadSession } from "./utils/helpers";
import * as AuthAPI from "./api/Auth.ts";
import * as CommentAPI from "./api/Comment.ts";
import * as Builders from "./utils/builders.ts";

chrome.runtime.onMessage.addListener(
  (message: RuntimeMessage, _, sendResponse) => {
    console.log("got a message: ", message);
    if (message.action === "login" || message.action === "register") {
      handleAuthenticationMessaging(message).then((response) => {
        sendResponse(response);
      });
    } else {
      handleAuthenticatedMessaging(message).then((response) => {
        sendResponse(response);
      });
    }

    return true;
  },
);

/**
 * Handles baseline messaging, without a session token in the header, intended for login and registration purposes
 * @param message message/request that should be sent
 * @returns response to the message, usually sessionToken
 */
async function handleAuthenticationMessaging(message: RuntimeMessage) {
  const placeholderOriginId =
    "chromeExtensionIdNotHardcodedHereButInEnvironmentalVariables";
  const originHeader = Builders.buildOriginHeader(placeholderOriginId);
  switch (message.action) {
    case "login": {
      const apiRequest = Builders.buildApiRequest(
        originHeader,
        message.payload,
      );
      return await AuthAPI.handleLogin(apiRequest);
    }

    case "register": {
      const apiRequest = Builders.buildApiRequest(
        originHeader,
        message.payload,
      );
      const response = await AuthAPI.handleReregister(apiRequest);
      return response;
    }

    default:
      console.warn("Unknown action", message.action);
      return { error: "Unknown action" };
  }
}
/**
 * Handles Autheticated messaging, intended for anything and everything that requires a session token in the header to be valid (comments, pass changes, logout, etc)
 * @param message any message to be sent
 * @returns response to said message
 */
async function handleAuthenticatedMessaging(message: RuntimeMessage) {
  const placeholderOriginId =
    "chromeExtensionIdNotHardcodedHereButInEnvironmentalVariables";
  try {
    const sessionToken: SessionToken | undefined = await loadSession();

    if (!sessionToken) throw new Error("Unauthorized");
    const authHeader = Builders.buildAuthHeader(
      sessionToken.sessionId,
      placeholderOriginId,
    );

    switch (message.action) {
      case "logout": {
        const authHeader = Builders.buildAuthHeader(
          sessionToken.sessionId,
          placeholderOriginId,
        );
        return await AuthAPI.handleLogout(authHeader);
      }
      case "comment": {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.comment(apiRequest);
      }

      case "edit": {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.edit(apiRequest);
      }
      case "getComments": {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.getAll(apiRequest);
      }
      case "deleteComment": {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.deleteComment(apiRequest);
      }

      default:
        console.warn("Unknown action", message.action);
        return { error: "Unknown action" };
    }
  } catch (error) {
    console.error("Background script error:", error);
    return { error: error instanceof Error ? error.message : "Unknown error" };
  }
}
