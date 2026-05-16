import { useState } from "react";
import { CommentView } from "../../features/comment/components/CommentView";
import { useVoxPopuliData } from "../../shared/hooks/DataHook";
import { useKeyboardShortcut } from "../../shared/hooks/KeyboardHook";

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
