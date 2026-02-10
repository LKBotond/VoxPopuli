import type { View } from "./View";

export interface ViewProps {
  changeViewTo: (view: View) => void;
}
