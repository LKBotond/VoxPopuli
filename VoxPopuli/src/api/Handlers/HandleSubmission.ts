import { sendMessage } from "../VoxPopuliApi";
import type {
  LoginMessage,
  RegistrationMessage,
  LogoutMessage,
} from "../../types/MessageTypes";
import type { LoginRequest, RegistrationRequest } from "../../contracts/Auth";

export async function submitLoginForm(formData: FormData) {
  const loginRequest: LoginMessage = buildLoginMessage(formData);

  if (!integrityCheck(loginRequest.payload)) {
    console.error("Payload is missing required fields!");
    throw new Error("Missing Fields");
  }
  return sendMessage<boolean>(loginRequest);
}
export async function submitRegistrationForm(formData: FormData) {
  const registrationRequest: RegistrationMessage =
    buildRegistrationMessage(formData);

  if (!integrityCheck(registrationRequest.payload)) {
    console.error("Payload is missing required fields!");
    throw new Error("Missing Fields");
  }
  return sendMessage<boolean>(registrationRequest);
}

export async function handleLogout() {
  const logoutMessage: LogoutMessage = buildLogoutMessage();
  return sendMessage<boolean>(logoutMessage);
}

function buildLoginMessage(formData: FormData): LoginMessage {
  const payload = mapToMessage<LoginRequest>(formData);
  return { action: "login", payload: payload };
}
function buildRegistrationMessage(formData: FormData): RegistrationMessage {
  const payload = mapToMessage<RegistrationRequest>(formData);
  return { action: "register", payload: payload };
}

function buildLogoutMessage(): LogoutMessage {
  return { action: "logout", payload: undefined };
}
function mapToMessage<T>(formData: FormData): T {
  return Object.fromEntries(formData.entries()) as T;
}

function integrityCheck<T extends object>(message: T): boolean {
  for (const value of Object.values(message)) {
    if (value === null || value === undefined || value === "") {
      return false;
    }
  }
  return true;
}
