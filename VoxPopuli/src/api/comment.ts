import type { ApiRequest, AuthHeader } from "../contracts/ApiRequest";
import type {
  CommentEditRequest,
  CommentRequest,
  CommentResponse,
} from "../contracts/Comment";
import { post, put, get, del } from "./VoxPopuliApi";

export async function comment(
  commentRequest: ApiRequest<AuthHeader, CommentRequest>,
): Promise<CommentResponse> {
  try {
    const commentResponse = await post<
      AuthHeader,
      CommentRequest,
      CommentResponse
    >("/comments", commentRequest);
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}

export async function edit(
  registrationRequest: ApiRequest<AuthHeader, CommentEditRequest>,
): Promise<CommentResponse> {
  try {
    const commentResponse = await put<
      AuthHeader,
      CommentEditRequest,
      CommentResponse
    >("/comments", registrationRequest);
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}

export async function getAll(
  getRequest: ApiRequest<AuthHeader, string>,
): Promise<CommentResponse[]> {
  try {
    const commentResponse = await get<CommentResponse[]>(
      "/comments/" + getRequest,
      getRequest.headers,
    );
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}
export async function deleteComment(
  deleteRequest: ApiRequest<AuthHeader, string>,
): Promise<CommentResponse> {
  try {
    const commentResponse = await del<CommentResponse>(
      "/comments/" + deleteRequest,
      deleteRequest.headers,
    );
    console.log(commentResponse);
    return commentResponse;
  } catch (e) {
    console.log("error: " + e);
    throw e;
  }
}
