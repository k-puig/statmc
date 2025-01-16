import { McServerStatus } from "./App";
import styles from "./StatusTable.module.css";

function StatusTable({ mcServerStats }: {mcServerStats: McServerStatus[]}) {
  const statElements = new Array<JSX.Element>();
  mcServerStats.forEach(element => {
    statElements.push(
      <tr>
        <td className={styles.bordered}>{element.serverName}</td>
        <td className={styles.bordered}>{element.domain}</td>
        <td className={styles.bordered}>{element.port}</td>
        <td className={styles.bordered}>{element.online ? "ONLINE" : "OFFLINE"}</td>
        <td className={styles.bordered}>{element.version}</td>
        <td className={styles.bordered}>{element.playerCount}{"/"}{element.playerMax}</td>
      </tr>
    );
  });

  return (
    <>
      <table className={styles.bordered}>
        <thead>
          <tr>
            <th className={styles.bordered}>Server Name</th>
            <th className={styles.bordered}>Domain</th>
            <th className={styles.bordered}>Port</th>
            <th className={styles.bordered}>Status</th>
            <th className={styles.bordered}>Version</th>
            <th className={styles.bordered}>Player Count</th>
          </tr>
        </thead>
        <tbody>
          {mcServerStats.map((element) => (
            <tr key={element.domain + element.port}>
              <td className={styles.bordered}>{element.serverName}</td>
              <td className={styles.bordered}>{element.domain}</td>
              <td className={styles.bordered}>{element.port}</td>
              <td className={styles.bordered}>{element.online ? "ONLINE" : "OFFLINE"}</td>
              <td className={styles.bordered}>{element.version}</td>
              <td className={styles.bordered}>
                {element.playerCount}/{element.playerMax}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}

export default StatusTable;