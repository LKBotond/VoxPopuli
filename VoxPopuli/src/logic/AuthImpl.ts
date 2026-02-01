import { redirect, saveSession } from "../helpers/helpers";
import { Session } from "../types/Session";

export async function register(registrationRequest: FormData) {
  const session: Session = await chrome.runtime.sendMessage({
    action: "register",
    payload: registrationRequest,
  });
  await saveSession(session);
  redirect("html/Interior.html");
}

export async function login(loginRequest: FormData) {
  const session: Session = await chrome.runtime.sendMessage({
    action: "login",
    payload: loginRequest,
  });
  await saveSession(session);
  redirect("html/Interior.html");
}
