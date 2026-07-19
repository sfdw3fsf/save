export const nameIdValueToOptions = (d: { name: string; id: number }[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.id,
  }));
};
