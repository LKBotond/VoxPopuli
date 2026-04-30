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
