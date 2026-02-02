import { redirect, saveSession } from "../helpers/helpers";
import { LoginMessage, RegistrationMessage } from "../types/MessageTypes";
import {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "../types/VoxPopuliTypes";

export async function register(registrationRequest: RegistrationRequest) {
  const session: SessionToken =
    await chrome.runtime.sendMessage<RegistrationMessage>({
      action: "register",
      payload: registrationRequest,
    });
  await saveSession(session);
  redirect("html/Interior.html");
}

export async function login(loginRequest: LoginRequest) {
  const session: SessionToken = await chrome.runtime.sendMessage<LoginMessage>({
    action: "login",
    payload: loginRequest,
  });
  await saveSession(session);
  redirect("html/Interior.html");
}
