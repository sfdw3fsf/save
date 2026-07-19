import { useState } from 'react';

export function useSelectTable<T extends { key: React.Key }>(checkedSelectedRecords = false) {
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  // const selectedRowRecords = useRef<T[]>([]);
  const [selectedRowRecords, setSelectedRowRecords] = useState<T[]>([]);

  const handleSelect = (record: T, selected: boolean, _: T[]) => {
    const { key } = record;

    setSelectedRowKeys((prevSelectedIds) => {
      if (selected) {
        if (checkedSelectedRecords)
          // selectedRowRecords.current = [record, ...selectedRowRecords.current];
          setSelectedRowRecords([record, ...selectedRowRecords]);
        return [key, ...prevSelectedIds];
      }
      if (checkedSelectedRecords)
        // selectedRowRecords.current = selectedRowRecords.current.filter((r) => r.key !== key);
        setSelectedRowRecords(selectedRowRecords.filter((r) => r.key !== key));
      return prevSelectedIds.filter((id) => id !== key);
    });
  };

  const handleSelectAll = (selected: boolean, _: T[], changeRows: T[]) => {
    if (selected) {
      const newSelectedKeys = changeRows.map((row) => row.key);

      setSelectedRowKeys((e) => [...newSelectedKeys, ...e]);
      if (checkedSelectedRecords)
        // selectedRowRecords.current = [...changeRows, ...selectedRowRecords.current];
        setSelectedRowRecords([...changeRows, ...selectedRowRecords]);
    } else {
      setSelectedRowKeys((e) => e.filter((k) => !changeRows.some((check) => check.key === k)));
      if (checkedSelectedRecords)
        // selectedRowRecords.current = selectedRowRecords.current.filter(
        //   (r) => !changeRows.some((check) => check.key === r.key),
        // );
        setSelectedRowRecords(selectedRowRecords.filter((r) => !changeRows.some((check) => check.key === r.key)));
    }
  };

  return {
    selectedRowKeys,
    setSelectedRowKeys,
    handleSelect,
    handleSelectAll,
    selectedRowRecords,
    setSelectedRowRecords,
  };
}
