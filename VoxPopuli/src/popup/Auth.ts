import { collectFormData, validateFormdata } from "../helpers/helpers";
import { login, register } from "../logic/AuthImpl";

const actions =
  document.querySelectorAll<HTMLButtonElement>("#authForm button");
actions.forEach((action) => {
  action.addEventListener("click", async (event) => {
    event.preventDefault();
    try {
      const data: FormData = collectFormData("authForm");
      if (!validateFormdata(data)) {
        alert("Fill out every field please and thank you.");
        return;
      }
      const target = event.currentTarget as HTMLButtonElement;
      if (target.id === "login") {
        await login(data);
      } else if (target.id === "register") {
        await register(data);
      } else {
        console.warn("Unknown button clicked in auth form");
      }
    } catch (error) {
      console.error(error);
      alert("An error occurred during authentication.");
    }
  });
});
