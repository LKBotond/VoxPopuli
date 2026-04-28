import { useState } from "react";
import type { CommentResponse } from "../../contracts/Comment";
import * as UI from "../Index";
type CommentNode = CommentResponse & {
  replies: CommentNode[];
};
type CommentProps = {
  comment: CommentNode;
  onReply: (parentId: string | undefined, content: string) => void;
};
function Comment({ comment, onReply }: CommentProps) {
  const [showReplyForm, setShowReplyForm] = useState(false);
  return (
    <UI.Div style={{ marginLeft: "20px" }}>
      <UI.H>{`${comment.alias} (says)`}</UI.H>
      <UI.P>{comment.content}</UI.P>

      <UI.Button onClick={() => setShowReplyForm((status) => !status)}>
        {showReplyForm ? "Cancel" : "Reply"}
      </UI.Button>

      {showReplyForm && (
        <UI.CommentForm
          parentId={comment.commentId}
          handleComment={(parentId, content) => {
            onReply(parentId, content);
            setShowReplyForm(false);
          }}
        />
      )}

      {comment.replies?.map((reply) => (
        <Comment key={reply.commentId} comment={reply} onReply={onReply} />
      ))}
    </UI.Div>
  );
}
export default Comment;
