import type {
  CommentRequest,
  CommentResponse,
  CommentEditRequest,
} from "../contracts/Comment";
import { post, put, get, del } from "./VoxPopuliApi";
export async function comment(
  commentRequest: CommentRequest,
): Promise<CommentResponse> {
  try {
    const commentResponse = await post<CommentRequest, CommentResponse>(
      "/comments",
      commentRequest,
    );
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}
export async function edit(
  registrationRequest: CommentEditRequest,
): Promise<CommentResponse> {
  try {
    const commentResponse = await put<CommentEditRequest, CommentResponse>(
      "/comments",
      registrationRequest,
    );
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}

export async function getAll(sourceLink: string): Promise<CommentResponse[]> {
  try {
    const commentResponse = await get<CommentResponse[]>(
      "/comments/" + sourceLink,
    );
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}
export async function deleteComment(
  commentID: string,
): Promise<CommentResponse> {
  try {
    const commentResponse = await del<CommentResponse>(
      "/comments/" + commentID,
    );
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}
