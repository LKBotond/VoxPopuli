import type { CommentResponse } from "../../contracts/Comment";
import * as UI from "../Index";
interface CommentProps {
commentResponse:CommentResponse
}
function Comment(comment: CommentProps) {
  return (
    <UI.Div>
      <UI.H>{comment.commentResponse.}</UI.H>
      <UI.P>{comment.content}</UI.P>
      <UI.Button>Repply</UI.Button>
    </UI.Div>
  );
}
export default Comment;
