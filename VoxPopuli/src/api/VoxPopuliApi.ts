import { useQuery } from "@tanstack/react-query";
import { basePath } from "../contracts/Path";

export async function post<req,res>(path: string, payload: req): Promise<res> {
  const response = await fetch(basePath + path, {
    method: "POST",
    headers: {
      //need to check for my headers
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error("API request failed: " + response.status);
  }

  return response.json();
}

export async function put<T>(path: string, payload: T): Promise<T> {
  const response = await fetch(basePath + path, {
    method: "PUT",
    headers: {
      //need to check for my headers
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });
  if (!response.ok) {
    throw new Error("API request failed, " + response.status);
  }
  return response.json();
}
export async function get<T>(path: string): Promise<T> {
  const response = await fetch(basePath + path, {
    method: "GET",
    headers: {
      //need to check for my headers
      "Content-Type": "application/json",
    },
  });
  if (!response.ok) {
    throw new Error("API request failed, " + response.status);
  }
  return response.json();
}

export function useApiToGet<T>(key: string, path: string) {
  return useQuery<T, Error>({
    queryKey: [key, path],
    queryFn: () => get<T>(path),
  });
}

export async function sendMessage<T>(message: object): Promise<T> {
  return new Promise<T>((resolve, reject) => {
    chrome.runtime.sendMessage(message, (response) => {
      if (chrome.runtime.lastError) {
        reject(chrome.runtime.lastError);
      } else resolve(response as T);
    });
  });
}
