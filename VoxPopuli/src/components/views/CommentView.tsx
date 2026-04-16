import type { CommentResponse } from "../../contracts/Comment";
import * as UI from "../Index";
interface CommentViewProps {
  comments: CommentResponse[];
}
export function CommentView(ViewProps: CommentViewProps) {
  return (
    <UI.Section className="grid-cols-5">
      <UI.Div className="col-span-3"></UI.Div>
      <UI.Div className="col-span-2 bg-gray-900">
        <UI.H>Vox Populi</UI.H>
        <UI.P>Let the people hear your voice</UI.P>
      </UI.Div>
    </UI.Section>
  );
}
