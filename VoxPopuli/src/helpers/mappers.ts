import { LoginRequest, RegistrationRequest } from "../types/VoxPopuliTypes";

export function mapFormDataToLoginRequest(fd: FormData): LoginRequest {
  const email = fd.get("email");
  const pass = fd.get("pass");
  if (typeof email !== "string" || typeof pass !== "string") {
    throw new Error("FormData missing required fields or has invalid types");
  }
  return { email, pass };
}
export function mapFormDataToRegistrationRequest(
  fd: FormData,
): RegistrationRequest {
  const email = fd.get("email");
  const alias = fd.get("alias");
  const passArray = fd.get("pass");
  if (
    typeof email !== "string" ||
    typeof alias !== "string" ||
    typeof passArray !== "string"
  ) {
    throw new Error("FormData missing required fields or has invalid types");
  }
  return { email, alias, passArray };
}
