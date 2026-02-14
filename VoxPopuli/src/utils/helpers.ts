import type { SessionToken } from "../contracts/Auth";
export function collectFormData(formId: string): FormData {
  const form = document.getElementById(formId) as HTMLFormElement;
  return new FormData(form);
}

export function validateFormData(data: FormData): boolean {
  for (const [key, value] of data.entries()) {
    if (!value || value.toString().trim() === "") {
      return false;
    }
    if (!key || key.trim() === "") {
      return false;
    }
  }
  return true;
}

export async function saveSession(sessionToken: SessionToken): Promise<void> {
  await chrome.storage.session.set({ session: sessionToken });
}

export async function loadSession(): Promise<SessionToken | undefined> {
  const { session } = await chrome.storage.session.get("session");
  return session as SessionToken | undefined;
}

export function jsonify(input: object): string {
  return JSON.stringify(input);
}

export function setPage(path: string): void {
  chrome.action.setPopup({ popup: path });
}
export function redirect(path: string): void {
  window.location.href = path;
}
