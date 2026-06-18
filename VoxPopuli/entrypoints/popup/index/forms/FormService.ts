import { sendMessage } from "@/entrypoints/common/service/frontend/Messaging";
import type {
  LoginMessage,
  RegistrationMessage,
} from "@/entrypoints/common/types/MessageTypes";

import * as Actions from "@/entrypoints/common/types/Actions";
import type {
  LoginRequest,
  RegistrationRequest,
} from "@/entrypoints/common/contracts/Auth";

export async function submitLoginForm(formData: FormData) {
  const loginRequest: LoginMessage = buildLoginMessage(formData);

  if (!integrityCheck(loginRequest.payload)) {
    console.error("Payload is missing required fields!");
    throw new Error("Missing Fields");
  }
  return sendMessage(loginRequest);
}
export async function submitRegistrationForm(formData: FormData) {
  const registrationRequest: RegistrationMessage =
    buildRegistrationMessage(formData);

  if (!integrityCheck(registrationRequest.payload)) {
    console.error("Payload is missing required fields!");
    throw new Error("Missing Fields");
  }
  return sendMessage(registrationRequest);
}

function buildLoginMessage(formData: FormData): LoginMessage {
  const payload = mapToMessage<LoginRequest>(formData);
  return { action: Actions.LOGIN, payload: payload };
}
function buildRegistrationMessage(formData: FormData): RegistrationMessage {
  const payload = mapToMessage<RegistrationRequest>(formData);
  return { action: Actions.REGISTER, payload: payload };
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
