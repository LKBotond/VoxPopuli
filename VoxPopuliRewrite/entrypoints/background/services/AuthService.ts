import { browser } from "wxt/browser";
import type {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "@/entrypoints/common/contracts/Auth";
import type {
  ApiRequest,
  AuthHeader,
  OriginHeader,
} from "@/entrypoints/common/contracts/ApiRequest";
import { saveSession, endSession } from "@/entrypoints/common/utils/helpers";
import { del, post } from "@/entrypoints/common/service/backend/VoxPopuliApi";

export async function handleReregister(
  registrationRequest: ApiRequest<OriginHeader, RegistrationRequest>,
): Promise<boolean> {
  try {
    const sessionToken = await post<
      OriginHeader,
      RegistrationRequest,
      SessionToken
    >("/auth/register", registrationRequest);
    await saveSession(sessionToken);
    browser.action.setPopup({ popup: "interior.html" });
    return true;
  } catch (e) {
    console.error("error: " + e);
    return false;
  }
}
export async function handleLogin(
  registrationRequest: ApiRequest<OriginHeader, LoginRequest>,
): Promise<boolean> {
  try {
    const sessionToken = await post<OriginHeader, LoginRequest, SessionToken>(
      "/auth/login",
      registrationRequest,
    );
    saveSession(sessionToken);
    browser.action.setPopup({ popup: "interior.html" });
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}

export async function handleLogout(
  logoutRequest: AuthHeader,
): Promise<boolean> {
  try {
    await del("/auth/logout", logoutRequest);
    await endSession();
    browser.action.setPopup({ popup: "index.html" });
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}
