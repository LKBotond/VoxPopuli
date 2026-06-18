import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { InteriorView } from "./views/InteriorView";
import "@/entrypoints/common/styles/Tailwind.css"
import Main from "@/entrypoints/common/components/generics/Main";

const queryClient = new QueryClient();
function InteriorApp() {
  const renderView = () => {
    return <InteriorView />;
  };

  return (
    <QueryClientProvider client={queryClient}>
      <Main>{renderView()}</Main>
    </QueryClientProvider>
  );
}

export default InteriorApp;
