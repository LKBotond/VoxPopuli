import type { HTMLAttributes } from "react";

interface Hprops extends HTMLAttributes<HTMLHeadingElement> {
  className?: string;
  priority?: 1 | 2 | 3 | 4 | 5 | 6;
}

function H({ priority = 1, className = "", ...props }: Hprops) {
  const Tag = `h${priority}` as "h1" | "h2" | "h3" | "h4" | "h5" | "h6";
  const baseClasses = "font-bold text-gray-200";

  return <Tag className={`${baseClasses} ${className}`} {...props} />;
}

H.displayName = "H";

export default H;
