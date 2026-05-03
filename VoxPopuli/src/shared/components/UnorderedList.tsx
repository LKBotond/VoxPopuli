import type { HTMLAttributes } from "react";
interface ULProps extends HTMLAttributes<HTMLUListElement> {
  className?: string;
}
function UL({
  className = "",
  children,
  ...props
}: ULProps) {
  const baseClasses =
    "flex w-1/2 flex-col p-4 bg-neutral-750 rounded-md shadow-sm";
  return (
    <ul className={`${baseClasses} ${className}`} {...props}>
      {children}
    </ul>
  );
}
export default UL;
