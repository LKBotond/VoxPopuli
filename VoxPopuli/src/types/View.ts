import { VIEWS } from "./Constants";

export type View = typeof VIEWS[keyof typeof VIEWS];