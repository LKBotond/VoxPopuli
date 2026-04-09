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
import { SESSION_HEADER, EXTENSION_ID } from "./contracts/NamingConventions.ts";
//call the helpers
chrome.runtime.onMessage.addListener(
  (message: RuntimeMessage, _, sendResponse) => {
    if (message.action === "login" || message.action === "register") {
      handleAuthMessaging(message).then((response) => {
        sendResponse(response);
      });
    } else {
      handleCommentMessaging(message).then((response) => {
        sendResponse(response);
      });
    }

    return true;
  },
);

async function handleAuthMessaging(message: RuntimeMessage) {
  const placeholderOriginId = "placeholder";
  const originHeader = buildOriginHeader(placeholderOriginId);
  switch (message.action) {
    case "login": {
      const apiRequest = buildApiRequest(originHeader, message.payload);
      return await AuthAPI.login(apiRequest);
    }

    case "register": {
      const apiRequest = buildApiRequest(originHeader, message.payload);
      return await AuthAPI.register(apiRequest);
    }
  }
}
async function handleCommentMessaging(message: RuntimeMessage) {
  const placeholderOriginId = "placeholder";
  try {
    const sessionToken: SessionToken | undefined = await loadSession();
    if (!sessionToken) throw new Error("Unauthorized");
    const authHeader = buildAuthHeader(
      sessionToken.sessionId,
      placeholderOriginId,
    );

    switch (message.action) {
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
    [SESSION_HEADER]: sessionId,
    [EXTENSION_ID]: extensionId,
  };
}
function buildOriginHeader(extensionId: string): OriginHeader {
  return {
    [EXTENSION_ID]: extensionId,
  };
}

function buildApiRequest<header, payload>(
  headers: header,
  jsonified: payload,
): ApiRequest<header, payload> {
  const apiRequest: ApiRequest<header, payload> = {
    headers: headers,
    payload: jsonified,
  };
  return apiRequest;
}
