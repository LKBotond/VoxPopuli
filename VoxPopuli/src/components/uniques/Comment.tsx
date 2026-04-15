import * as UI from "../Index";
interface CommentProps {
  id: string;
  parentId: string;
  alias: string;
  content: string;
}
function Comment(comment: CommentProps) {
  return (
    <UI.Div>
      <UI.H>{comment.alias}</UI.H>
      <UI.P>{comment.content}</UI.P>
      <UI.Button>Repply</UI.Button>
    </UI.Div>
  );
}
export default Comment;
