import { Button, Menu, Box } from "@mantine/core";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import ReactCountryFlag from "react-country-flag";
import type { JSX } from "react/jsx-runtime";

type LanguageOption = {
  code: string;
  label: string;
  icon: JSX.Element;
};

const languages: LanguageOption[] = [
  { code: "uk", label: "Українська", icon: <ReactCountryFlag countryCode="UA" svg style={{ width: 20, height: 20 }} /> },
  { code: "en", label: "English", icon: <ReactCountryFlag countryCode="GB" svg style={{ width: 20, height: 20 }} /> },
  { code: "zh", label: "中文", icon: <ReactCountryFlag countryCode="CN" svg style={{ width: 20, height: 20 }} /> },
];

function LanguageMenu() {
  const { i18n } = useTranslation();
  const currentLanguage = languages.find((l) => l.code === i18n.language) || languages[1];
  const [selected, setSelected] = useState<LanguageOption>(currentLanguage);

  const handleSelect = (lang: LanguageOption) => {
    i18n.changeLanguage(lang.code);
    setSelected(lang);
  };

  return (
    <Box w={200}>
      <Menu>
        <Menu.Target>
          <Button
            leftSection={selected.icon}
            color="dark"
            fullWidth
            styles={{ root: { justifyContent: "flex-start" } }}
          >
            {selected.label}
          </Button>
        </Menu.Target>

        <Menu.Dropdown bg="dark">
          {languages.map((lang) => (
            <Menu.Item
              key={lang.code}
              leftSection={lang.icon}
              bg="dark"
              c="white"
              onClick={() => handleSelect(lang)}
            >
              {lang.label}
            </Menu.Item>
          ))}
        </Menu.Dropdown>
      </Menu>
    </Box>
  );
}

export default LanguageMenu;
