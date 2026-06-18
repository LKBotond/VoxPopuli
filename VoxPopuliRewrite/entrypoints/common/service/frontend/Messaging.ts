import { browser } from "wxt/browser";

export function sendMessage<T_Message, T_Response>(
  message: T_Message,
): Promise<T_Response> {
  return browser.runtime.sendMessage(message);
}
