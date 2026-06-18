import { useState } from "react";
import { CommentView } from "./view/CommentView";
import { useVoxPopuliData } from "./hooks/DataHook";
import { useKeyboardShortcut } from "./hooks/KeyboardHook";

function ContentApp() {
  const [isVisible, setIsVisible] = useState(false);
  const { comments, userAlias, sourceLinkHash, addComment } =
    useVoxPopuliData();

  useKeyboardShortcut("k", () => setIsVisible((prev) => !prev));

  return (
    <CommentView
      comments={comments}
      userAlias={userAlias}
      sourceLinkHash={sourceLinkHash}
      addComment={addComment}
      className={
        isVisible
          ? "opacity-100 scale-100"
          : "opacity-0 scale-95 pointer-events-none"
      }
    />
  );
}
export default ContentApp;
