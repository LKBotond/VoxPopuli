import type { HTMLAttributes } from "react";
interface UnorderedListProps extends HTMLAttributes<HTMLUListElement> {
  className?: string;
}
function UnorderedList({
  className = "",
  children,
  ...props
}: UnorderedListProps) {
  const baseClasses =
    "flex w-full flex-col p-4 rounded-md shadow-sm";
  return (
    <ul className={`${baseClasses} ${className}`} {...props}>
      {children}
    </ul>
  );
}
export default UnorderedList;
