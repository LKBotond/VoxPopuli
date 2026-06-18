import type { LoginRequest, RegistrationRequest } from "../contracts/Auth";
import * as Id from "../contracts/NamingConventions";

export function mapFormDataToLoginRequest(fd: FormData): LoginRequest {
  const email = fd.get(Id.EMAIL);
  const pass = fd.get(Id.PASSWORD);
  if (typeof email !== "string" || typeof pass !== "string") {
    throw new Error("FormData missing required fields or has invalid types");
  }
  return { email, pass };
}

export function mapFormDataToRegistrationRequest(
  fd: FormData,
): RegistrationRequest {
  const email = fd.get(Id.EMAIL);
  const alias = fd.get(Id.ALIAS);
  const passArray = fd.get(Id.PASSWORD);
  if (
    typeof email !== "string" ||
    typeof alias !== "string" ||
    typeof passArray !== "string"
  ) {
    throw new Error("FormData missing required fields or has invalid types");
  }
  return { email, alias, passArray };
}
