import type { RuntimeMessage } from "./types/MessageTypes";
import type { SessionToken } from "./contracts/Auth";
import { loadSession } from "./utils/helpers";
import * as AuthAPI from "./api/Auth.ts";
import * as CommentAPI from "./api/Comment.ts";
import type {
  ApiRequest,
  AuthHeader,
  OriginHeader,
} from "./contracts/ApiRequest.ts";
import {
  SESSION_HEADER,
  EXTENSION_ID,
  CONTENT_TYPE,
} from "./contracts/NamingConventions.ts";
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
  const originHeader = buildOriginHeader(placeholderOriginId);
  switch (message.action) {
    case "login": {
      const apiRequest = buildApiRequest(originHeader, message.payload);
      return await AuthAPI.login(apiRequest);
    }

    case "register": {
      const apiRequest = buildApiRequest(originHeader, message.payload);
      const response = await AuthAPI.register(apiRequest);
      return response;
    }
  }
}
/**
 * Handles Autheticated messaging, iontended for anything and everything that requires a session token in the header to be valid (comments, pass changes, logout, etc)
 * @param message any message to be sent
 * @returns response to said message
 */
async function handleAuthenticatedMessaging(message: RuntimeMessage) {
  const placeholderOriginId =
    "chromeExtensionIdNotHardcodedHereButInEnvironmentalVariables";
  try {
    const sessionToken: SessionToken | undefined = await loadSession();
    if (!sessionToken) throw new Error("Unauthorized");
    const authHeader = buildAuthHeader(
      sessionToken.sessionId,
      placeholderOriginId,
    );

    switch (message.action) {
      case "logout": {
        const authHeader = buildAuthHeader(
          sessionToken.sessionId,
          placeholderOriginId,
        );
        return await AuthAPI.logout(authHeader);
      }
      case "comment": {
        const apiRequest = buildApiRequest(authHeader, message.payload);
        return await CommentAPI.comment(apiRequest);
      }

      case "edit": {
        const apiRequest = buildApiRequest(authHeader, message.payload);
        return await CommentAPI.edit(apiRequest);
      }
      case "getComments": {
        const apiRequest = buildApiRequest(authHeader, message.payload);
        return await CommentAPI.getAll(apiRequest);
      }
      case "deleteComment": {
        const apiRequest = buildApiRequest(authHeader, message.payload);
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

function buildAuthHeader(sessionId: string, extensionId: string): AuthHeader {
  return {
    [CONTENT_TYPE]: "application/json",
    [SESSION_HEADER]: sessionId,
    [EXTENSION_ID]: extensionId,
  };
}
function buildOriginHeader(extensionId: string): OriginHeader {
  return {
    [CONTENT_TYPE]: "application/json",
    [EXTENSION_ID]: extensionId,
  };
}

function buildApiRequest<header, payload = undefined>(
  headers: header,
  jsonified?: payload,
): ApiRequest<header, payload> {
  const apiRequest: ApiRequest<header, payload> = {
    headers: headers,
    payload: jsonified,
  };
  return apiRequest;
}
