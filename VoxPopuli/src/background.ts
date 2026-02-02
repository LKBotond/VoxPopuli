chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
  switch (message.action) {
    case "login":
    case "register":
    case "logout":
    case "delete":
    default:
  }
});
