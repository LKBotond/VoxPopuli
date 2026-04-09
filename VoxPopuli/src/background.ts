import type { CommentEditRequest, CommentRequest } from "./contracts/Comment";
import type { RuntimeMessage } from "./types/MessageTypes";
import type {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "./contracts/Auth";
import { loadSession } from "./utils/helpers";
import * as AuthAPI from "./api/Auth.ts";
import * as CommentAPI from "./api/Comment.ts";

//call the helpers
chrome.runtime.onMessage.addListener(
  (message: RuntimeMessage, _, sendResponse) => {
    handleMessaging(message).then((response) => {
      sendResponse(response);
    });
    return true;
  },
);

async function handleMessaging(message: RuntimeMessage) {
  try {
    const sessionToken: SessionToken | undefined = await loadSession();

    switch (message.action) {
      case "login":
        return await AuthAPI.login(message.payload as LoginRequest);

      case "register":
        return await AuthAPI.register(message.payload as RegistrationRequest);

      case "getComments":
        if (!sessionToken) throw new Error("Unauthorized");
        return await CommentAPI.getAll(message.payload as string);

      case "comment":
        if (!sessionToken) throw new Error("Unauthorized");
        return await CommentAPI.comment(message.payload as CommentRequest);

      case "edit":
        if (!sessionToken) throw new Error("Unauthorized");
        return await CommentAPI.edit(
          message.payload as CommentEditRequest
        );
      case "deleteComment":
        if (!sessionToken) throw new Error("Unauthorized");
        return await CommentAPI.deleteComment(message.payload as string);

      default:
        console.warn("Unknown action", message.action);
        return { error: "Unknown action" };
    }
  } catch (error) {
    console.error("Background script error:", error);
    return { error: error instanceof Error ? error.message : "Unknown error" };
  }
}
