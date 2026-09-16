import { test, expect } from '@playwright/test';

test.describe('frete funcional', () => {

  test('calcula frete de R$ 15,00 para CEP iniciado em 8', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('87000000');
    await page.getByLabel('Valor do pedido').fill('100');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('status')).toHaveText('Frete: R$ 15,00');
  });


  test('calcula frete de R$ 25,00 para CEP não iniciado em 8', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('01000000');
    await page.getByLabel('Valor do pedido').fill('100');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('status')).toHaveText('Frete: R$ 25,00');
  });


  test('valor 199,99 fica abaixo do limite de frete grátis', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('87000000');
    await page.getByLabel('Valor do pedido').fill('199,99');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('status')).toHaveText('Frete: R$ 15,00');
  });


  test('valor 200,00 recebe frete grátis no limite', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('87000000');
    await page.getByLabel('Valor do pedido').fill('200,00');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('status')).toHaveText('Frete grátis');
  });


  test('valor 200,01 recebe frete grátis acima do limite', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('87000000');
    await page.getByLabel('Valor do pedido').fill('200,01');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('status')).toHaveText('Frete grátis');
  });


  test('rejeita CEP com apenas 7 dígitos', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('8700000');
    await page.getByLabel('Valor do pedido').fill('100');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('alert')).toHaveText('Dados inválidos');
  });


  test('rejeita CEP contendo letra', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('8700000A');
    await page.getByLabel('Valor do pedido').fill('100');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('alert')).toHaveText('Dados inválidos');
  });


  test('rejeita valor zero', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('87000000');
    await page.getByLabel('Valor do pedido').fill('0');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('alert')).toHaveText('Dados inválidos');
  });


  test('rejeita valor negativo', async ({ page }) => {
    await page.goto('/frete');

    await page.getByLabel('CEP').fill('87000000');
    await page.getByLabel('Valor do pedido').fill('-1');

    await page.getByRole('button', { name: 'Calcular frete' }).click();

    await expect(page.getByRole('alert')).toHaveText('Dados inválidos');
  });

});