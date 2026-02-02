import * as Api from "./logic/VoxPopuliAPI";
import { RuntimeMessage } from "./types/MessageTypes";
import { SessionToken } from "./types/VoxPopuliTypes";

chrome.runtime.onMessage.addListener(
  async (message: RuntimeMessage, sender, sendResponse) => {
    switch (message.action) {
      case "login": {
        const response: SessionToken | undefined = await Api.login(
          message.payload,
        );
        sendResponse(response);
        return true;
      }
      case "register": {
        const response: SessionToken | undefined = await Api.register(
          message.payload,
        );
        sendResponse(response);
        return true;
      }
      default:
    }
  },
);
