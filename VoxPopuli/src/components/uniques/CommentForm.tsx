import { useState } from "react";
import type { FormProps } from "../generics/Form";
import * as UI from "../Index";

interface CommentFormProps extends FormProps {
  parentId?: string;
  handleComment: (parentId: string | undefined, content: string) => void;
}

function CommentForm(formProps: CommentFormProps) {
  const [content, setContent] = useState("");
  const onSubmit = (e: React.SubmitEvent) => {
    e.preventDefault();
    formProps.handleComment(formProps.parentId, content);
    setContent("");
  };

  return (
    <UI.Form {...formProps} onSubmit={onSubmit}>
      <UI.Input
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="Your Opinion comes here"
      />
      <UI.Button type="submit">Create comment</UI.Button>
    </UI.Form>
  );
}

export default CommentForm;
