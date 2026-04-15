export function createShadowRoot() {
  const TAG_NAME = "vox-populi-shadow-root";
  const existingHost = document.querySelector(TAG_NAME);
  if (existingHost) {
    existingHost.remove();
  }
  const host = document.createElement(TAG_NAME);
  const target = document.body || document.documentElement;
  target.appendChild(host);

const shadow = host.attachShadow({ mode: "open" });
  return shadow;
}
