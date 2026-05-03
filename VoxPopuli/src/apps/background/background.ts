import type { RuntimeMessage } from "../../shared/api/frontend/MessageTypes.ts";
import type { SessionToken } from "../../shared/contracts/Auth.ts";
import { loadSession } from "../../shared/utils/helpers.ts";
import * as AuthAPI from "../../features/exterior/services/AuthService.ts";
import * as CommentAPI from "../../features/comment/services/backend/BackendCommentService.ts"
import * as Builders from "../../shared/utils/builders.ts";
import * as Actions from "../../shared/api/frontend/Actions"

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
    case Actions.LOGIN: {
      const apiRequest = Builders.buildApiRequest(
        originHeader,
        message.payload,
      );
      return await AuthAPI.handleLogin(apiRequest);
    }

    case Actions.REGISTER: {
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

    if (!sessionToken) {
      return [];
    }
    const authHeader = Builders.buildAuthHeader(
      sessionToken.sessionId,
      placeholderOriginId,
    );

    switch (message.action) {
      case Actions.LOGOUT: {
        const authHeader = Builders.buildAuthHeader(
          sessionToken.sessionId,
          placeholderOriginId,
        );
        return await AuthAPI.handleLogout(authHeader);
      }
      case Actions.COMMENT: {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.comment(apiRequest);
      }

      case Actions.EDIT: {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.edit(apiRequest);
      }
      case Actions.GET_COMMENTS: {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.getAll(apiRequest);
      }
      case Actions.DELETE_COMMENT: {
        const apiRequest = Builders.buildApiRequest(
          authHeader,
          message.payload,
        );
        return await CommentAPI.deleteComment(apiRequest);
      }
      case Actions.GET_ALIAS: {
        return sessionToken.alias;
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
