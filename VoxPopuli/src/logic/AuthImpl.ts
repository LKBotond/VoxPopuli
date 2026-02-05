import { redirect, saveSession } from "../helpers/helpers";
import {
  mapFormDataToLoginRequest,
  mapFormDataToRegistrationRequest,
} from "../helpers/mappers";
import { LoginMessage, RegistrationMessage } from "../types/MessageTypes";
import {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "../types/VoxPopuliTypes";

export async function register(inputForm: FormData) {
  const registrationRequest = mapFormDataToRegistrationRequest(inputForm);
  const session: SessionToken =
    await chrome.runtime.sendMessage<RegistrationMessage>({
      action: "register",
      payload: registrationRequest,
    });
  await saveSession(session);
  redirect("html/Interior.html");
}

export async function login(inputForm: FormData) {
  const loginRequest = mapFormDataToLoginRequest(inputForm);
  const session: SessionToken = await chrome.runtime.sendMessage<LoginMessage>({
    action: "login",
    payload: loginRequest,
  });
  await saveSession(session);
  redirect("html/Interior.html");
}
