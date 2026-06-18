import { sendMessage } from "@/entrypoints/common/service/frontend/Messaging";
import { LOGOUT } from "@/entrypoints/common/types/Actions";
import { LogoutMessage } from "@/entrypoints/common/types/MessageTypes";

export async function handleLogout() {
  const logoutMessage: LogoutMessage = buildLogoutMessage();
  return sendMessage(logoutMessage);
}

function buildLogoutMessage(): LogoutMessage {
  return { action: LOGOUT };
}
