import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import Button from "../generics/Button";
import Form from "../generics/Form";
import Input from "../generics/Input";

export function LoginView({ changeViewTo }: ViewProps) {
  return (
    <Form>
      <Input placeholder="email" required type="text"></Input>
      <Input placeholder="password" required type="password"></Input>
      <Button onClick={() => changeViewTo(VIEWS.INTERIOR)}>Login</Button>
      <Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</Button>
    </Form>
  );
}
