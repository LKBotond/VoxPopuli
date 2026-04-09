import { useQuery } from "@tanstack/react-query";
import { basePath } from "../contracts/Path";

async function api<T>(path: string): Promise<T> {
  const response = await fetch(basePath + path);
  if (!response.ok) {
    throw new Error("API request failed, " + response.status);
  }
  return response.json();
}
export function useApi<T>(key: string, path: string) {
  return useQuery<T, Error>({
    queryKey: [key, path],
    queryFn: () => api<T>(path),
  });
}

export async function sendMessage<T>(message:object): Promise<T> {
  return new Promise<T>((resolve, reject) => {
    chrome.runtime.sendMessage(message, (response) => {
      if (chrome.runtime.lastError) {
        reject(chrome.runtime.lastError);
      } else resolve(response as T);
    });
  });
}
