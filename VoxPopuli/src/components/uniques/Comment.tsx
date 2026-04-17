import type { CommentNode } from "../../types/Props";
import * as UI from "../Index";
type CommentProps = {
  comment: CommentNode;
  onReply: (parentId: string) => void;
};
function Comment({ comment, onReply }: CommentProps) {
  return (
    <UI.Div style={{ marginLeft: "20px" }}>
      <UI.H>{`${comment.alias} (says)`}</UI.H>
      <UI.P>{comment.content}</UI.P>

      <UI.Button onClick={() => onReply(comment.commentId)}>
        Reply
      </UI.Button>

      {comment.replies?.map((reply) => (
        <Comment
          key={reply.commentId}
          comment={reply}
          onReply={onReply}
        />
      ))}
    </UI.Div>
  );
}
export default Comment;
