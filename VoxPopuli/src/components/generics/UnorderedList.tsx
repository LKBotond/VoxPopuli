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
    "flex w-full flex-col p-4 rounded-md shadow-sm";
  return (
    <ul className={`${baseClasses} ${className}`} {...props}>
      {children}
    </ul>
  );
}
export default UL;
