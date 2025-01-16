import { useEffect, useState } from "react";
import StatusTable from "./StatusTable";

export interface McServerStatus {
  serverName: string;
  domain: string;
  port: number;
  online: boolean;
  version: string;
  playerCount: number;
  playerMax: number;
}

function App() {
  const [mcStats, setMcStats] = useState(new Array<McServerStatus>());
  const [loaded, setLoaded] = useState(false);
  const [err, setErr] = useState(false);

  useEffect(() => {
    if (loaded) return;
    fetch("/statuses").then((res) => {
      res.json().then((json: McServerStatus[]) => {
        if (loaded) return;
        setLoaded(true);
        json.forEach(stat => {
          try {
            const name = JSON.parse(stat.serverName);
            stat.serverName = name["text"];
          // eslint-disable-next-line @typescript-eslint/no-unused-vars
          } catch (e) {
            console.log(stat.domain + ":" + stat.port + " is not a json");
          }
        });
        setMcStats(json);
      }).catch(() => {
        setErr(true);
      });
    }).catch(() => {
      setErr(true);
    });
  });

  if (err) {
    return (
      <>
        <button onClick={() => {setLoaded(false); setErr(false);}}>Refresh</button>
        <h1>Minecraft Server Statuses</h1>
        <div>
          Error loading stats. Please try refreshing.
        </div>
      </>
    )
  }

  if (!loaded && mcStats.length == 0) {
    return (
      <>
        <button onClick={() => {setLoaded(false); setErr(false);}}>Refresh</button>
        <h1>Minecraft Server Statuses</h1>
        <div>Loading...</div>
      </>
    );
  }

  return (
    <>
      <button onClick={() => {setLoaded(false); setErr(false);}}>Refresh</button>
      <h1>Minecraft Server Statuses</h1>
      <StatusTable mcServerStats={mcStats} />
    </>
  );
}

export default App
