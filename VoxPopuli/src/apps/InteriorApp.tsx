import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { InteriorView } from "../components/views/InteriorView";
import ".././styles/Tailwind.css";
import Main from "../components/generics/Main";

const queryClient = new QueryClient();
function App() {
  const renderView = () => {
    return <InteriorView />;
  };

  return (
    <QueryClientProvider client={queryClient}>
      <Main>{renderView()}</Main>
    </QueryClientProvider>
  );
}

export default App;
