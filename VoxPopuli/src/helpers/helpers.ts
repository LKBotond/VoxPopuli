import { SessionToken } from "../types/VoxPopuliTypes";
export function collectFormData(formId: string): FormData {
  const form = document.getElementById(formId) as HTMLFormElement;
  return new FormData(form);
}

export function validateFormdata(data: FormData): boolean {
  let incomplete: boolean = false;
  data.forEach((value, key) => {
    if (!value || value.toString().trim() === "") incomplete = true;
  });
  if (incomplete) {
    return false;
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
