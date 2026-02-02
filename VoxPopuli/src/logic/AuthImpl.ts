import { redirect, saveSession } from "../helpers/helpers";
import { SessionToken } from "../types/VoxPopuliTypes";

export async function register(registrationRequest: FormData) {
  const session: SessionToken = await chrome.runtime.sendMessage({
    action: "register",
    payload: registrationRequest,
  });
  await saveSession(session);
  redirect("html/Interior.html");
}

export async function login(loginRequest: FormData) {
  const session: SessionToken = await chrome.runtime.sendMessage({
    action: "login",
    payload: loginRequest,
  });
  await saveSession(session);
  redirect("html/Interior.html");
}
