import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import Button from "../generics/Button";
import Input from "../generics/Input";
import Form from "../generics/Form";

export function RegistrationView({ changeViewTo }: ViewProps) {
  return (
    <Form>
      <Input placeholder="email" required type="text"></Input>
      <Input placeholder="alias" required type="text"></Input>
      <Input placeholder="password" required type="password"></Input>
        <Button onClick={() => changeViewTo(VIEWS.INTERIOR)}>Register</Button>

        <Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</Button>

    </Form>
  );
}
