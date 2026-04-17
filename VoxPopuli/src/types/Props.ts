import type { View } from "./View";
import type { CommentResponse } from "../contracts/Comment";
export interface ViewProps {
  changeViewTo: (view: View) => void;
}
export interface CommentViewProps {
  comments: CommentResponse[];
}
export type CommentNode = CommentResponse & {
  replies: CommentNode[];
};
