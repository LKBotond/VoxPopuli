import { useState } from "react";
import { CommentView } from "../components/views/CommentView";
import { useVoxPopuliData } from "../hooks/DataHook";
import { useKeyboardShortcut } from "../hooks/KeyboardHook";

function App() {
  const [isVisible, setIsVisible] = useState(false);
  const { comments, userAlias, sourceLinkHash } = useVoxPopuliData();

  useKeyboardShortcut("k", () => setIsVisible((prev) => !prev));

  console.log("alias: " + userAlias);
  return (
    <CommentView
      comments={comments}
      userAlias={userAlias}
      sourceLinkHash={sourceLinkHash}
      className={
        isVisible
          ? "opacity-100 scale-100"
          : "opacity-0 scale-95 pointer-events-none"
      }
    />
  );
}
export default App;
