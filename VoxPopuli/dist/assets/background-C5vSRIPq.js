async function w() {
  const { session: n } = await chrome.storage.session.get("session");
  return n;
}
function a(n) {
  return JSON.stringify(n);
}
const l = "url",
  f = "/api/v1",
  o = "chromeExtensionIdNotHardcodedHereButInEnvironmentalVariables",
  u = d(o);
async function t(n, r, e, i) {
  const c = await fetch(l + f + n, { method: r, headers: e, body: i });
  if (!c.ok) throw new Error(`HTTP error ${c.status}`);
  return await c.json();
}
async function m(n) {
  return await t("/auth/login", "POST", u, a(n));
}
async function h(n) {
  return await t("/auth/register", "POST", u, a(n));
}
async function y(n, r) {
  const e = s(o, r);
  return await t("/comments/" + n, "GET", e);
}
async function E(n, r) {
  const e = s(o, r);
  return await t("/comments", "POST", e, a(n));
}
async function H(n, r) {
  const e = s(o, r);
  return await t("/comments", "PUT", e, a(n));
}
async function T(n, r) {
  const e = s(o, r);
  return await t("/comments/" + n, "DELETE", e);
}
function d(n) {
  return { "X-extension-id": n };
}
function s(n, r) {
  return { ...d(n), "X-session-id": r.sessionId, "X-alias": r.alias };
}
chrome.runtime.onMessage.addListener(
  (n, r, e) => (
    g(n).then((i) => {
      e(i);
    }),
    !0
  ),
);
async function g(n) {
  try {
    const r = await w();
    switch (n.action) {
      case "login":
        return await m(n.payload);
      case "register":
        return await h(n.payload);
      case "getComments":
        if (!r) throw new Error("Unauthorized");
        return await y(n.payload, r);
      case "comment":
        if (!r) throw new Error("Unauthorized");
        return await E(n.payload, r);
      case "edit":
        if (!r) throw new Error("Unauthorized");
        return await H(n.payload, r);
      case "deleteComment":
        if (!r) throw new Error("Unauthorized");
        return await T(n.payload, r);
      default:
        return (
          console.warn("Unknown action", n.action),
          { error: "Unknown action" }
        );
    }
  } catch (r) {
    return (
      console.error("Background script error:", r),
      { error: r instanceof Error ? r.message : "Unknown error" }
    );
  }
}
