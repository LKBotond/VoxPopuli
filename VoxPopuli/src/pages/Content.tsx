import { useEffect, useState } from "react";
import { CommentView } from "../components/views/CommentView";
import { sendMessage } from "../api/VoxPopuliApi";
import type { CommentGetMessage, GetAliasMessage } from "../types/MessageTypes";
import type { CommentResponse } from "../contracts/Comment";
import { hashString } from "../utils/hash";
function App() {
  const [comments, setComments] = useState<CommentResponse[]>([]);
  const [userAlias, setAlias] = useState<string>("");
  const [isVisible, setIsVisible] = useState<boolean>(false);
  const [sourceLinkHash, setSourceLinkHash] = useState<string>("");
  useEffect(() => {
    const getComments = async () => {
      const url = window.location.href;
      const hexUrl = await hashString(url);
      setSourceLinkHash(hexUrl);
      const message: CommentGetMessage = {
        action: "getComments",
        payload: hexUrl,
      };

      const result = await sendMessage<CommentResponse[]>(message);
      setComments(result);
    };

    getComments();
  }, []);

  useEffect(() => {
    const getAlias = async () => {
      const message: GetAliasMessage = {
        action: "getAlias",
        payload: null,
      };
      const result = await sendMessage<string>(message);
      setAlias(result);
    };
    getAlias();
  }, []);
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === "k") {
        e.preventDefault();
        setIsVisible((prev) => !prev);
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, []);
  console.log("alias: " + userAlias);
  return (
    <>
      {isVisible && (
        <CommentView
          comments={comments}
          userAlias={userAlias}
          sourceLinkHash={sourceLinkHash}
        />
      )}
    </>
  );
}
export default App;
