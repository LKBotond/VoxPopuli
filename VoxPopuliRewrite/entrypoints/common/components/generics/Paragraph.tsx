import type{HTMLAttributes} from "react";

interface PProps extends HTMLAttributes<HTMLParagraphElement> {
  className?: string;
}

function P({ className = "", ...props }: PProps) {
  const baseClasses = "w-full m-1 px-4 py-2 rounded text-gray-200 background";

  return <p className={`${baseClasses} ${className}`} {...props} />;
}

export default P;