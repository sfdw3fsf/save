export type CustomParagraphProps = {
  text: string;
};

export const CustomParagraph = ({ text }: CustomParagraphProps) => {
  const formattedText = text?.replace(/\n/g, '<br />');

  return <div className='text-[15px] text-neutral-100' dangerouslySetInnerHTML={{ __html: formattedText }}></div>;
};
